//Karol Kęciński, nr ind. 315550
#include "traceroute.h"
#include "icmp_receive.h"
#include "icmp_send.h"

int main(int argc, char *argv[])
{
    //Argument corectness check
    if (argc != 2)
    {
        printf("traceroute: fatal error: Wrong argument number.");
        return EXIT_FAILURE;
    }

    struct sockaddr_in recipient;
    bzero (&recipient, sizeof(recipient));
    recipient.sin_family = AF_INET;
    int32_t IP_VERIF = inet_pton(AF_INET, argv[1], &recipient.sin_addr);

    if(!IP_VERIF)
    {
        printf("traceroute: error: wrong ip address");
        return EXIT_FAILURE;
    }

    int sockfd = socket(AF_INET, SOCK_RAW, IPPROTO_ICMP);
	if (sockfd < 0) {
		fprintf(stderr, "traceroute: socket error: %s\n", strerror(errno)); 
		return EXIT_FAILURE;
	}

    int32_t PID = getpid();

    for(int i = 1; i <= MAX_TTL; i++)
    {
        int SOCKOPT_VERIF = setsockopt(sockfd, IPPROTO_IP, IP_TTL, &i, sizeof(int));

        if(SOCKOPT_VERIF < 0)
        {
            fprintf(stderr, "traceroute: fatal error - socket TTL error: %s\n", strerror(errno));
            return EXIT_FAILURE;
        }


        for(int try = 0; try < 3; try++)
        {
            ssize_t PKG_SEND_VER = send_package(sockfd, recipient, i, PID);
            if(PKG_SEND_VER < 0)
            {
                fprintf(stderr, "traceroute: error while sending package no. %d: %s\n", try, strerror(errno));
            }
        }
        
        if(receive(sockfd, PID, i) == 1)
            return EXIT_SUCCESS;
    }
    
    return EXIT_FAILURE;
}



