//Karol Kęciński, nr ind. 315550
#include "icmp_receive.h"

void print_as_bytes (unsigned char* buff, ssize_t length)
{
	for (ssize_t i = 0; i < length; i++, buff++)
		printf ("%.2x ", *buff);	
}


void print_row(int RCV_NUM, int avg, int TTL, char responses[3][20])
{
	if(TTL < 10)
		printf(" ");

	printf("%d.", TTL);

	if(RCV_NUM == 0)
	{
		printf(" *\n");
	} else {

		printf(" %-20s", responses[0]);

		if(strcmp(responses[0], responses[1]) != 0)
			printf(" %-20s", responses[1]);
		if(strcmp(responses[0], responses[2]) != 0 && strcmp(responses[1], responses[2]) != 0)
			printf(" %-20s", responses[2]);

		if(RCV_NUM < 3)
			printf("	???\n");
		else 
			printf("	%d ms\n", avg);
	}
}


int receive(int sockfd, int PID, int TTL)
{
	int DEST_REACHED = 0;
	char responses[3][20] = {"", "", ""};

	struct timeval time;
	gettimeofday(&time, NULL);
	long long start = ((long long)time.tv_sec * 1000) + (time.tv_usec / 1000);

	fd_set descriptors;
	FD_ZERO(&descriptors);
	FD_SET(sockfd, &descriptors);
	struct timeval tv;
	tv.tv_sec = 1;
	tv.tv_usec = 0;
	int time_passed = 0;
	int which = 0;
	int count = 0;

	while(which < 3)
	{
		int ready = select (sockfd+1, &descriptors, NULL, NULL, &tv);

		if(ready < 0)
		{
			fprintf(stderr, "traceroute: select error: %s\n", strerror(errno)); 
			which++;
		}
		if(ready > 0)
		{
			gettimeofday(&time, NULL);
			long long timeNow = ((long long)time.tv_sec * 1000) + (time.tv_usec / 1000);
			time_passed += timeNow - start;

			int result = receive_from(sockfd, PID, TTL, responses[which]);
			if(result == -1)
			{
				//fprintf(stderr, "traceroute: Package missed\n");
				continue;
			}
			if(result == 1)
			{
				DEST_REACHED = 1;
				count++;
			}
			if(result == 0)
			{
				count++;
			}

			which++;

			if(which == 3) 
			{ break; }
		}
		if(ready == 0)
		{
			which++;
		}
	}

	int avg;
	int RCV_NUM = 3;

	if(count < 3)
	{
		RCV_NUM = count;
	} else {
		avg = time_passed / 3;
	}

	print_row(RCV_NUM, avg, TTL, responses);

	return DEST_REACHED;
}

int receive_from(int sockfd, int PID, int TTL, char response[20])
{
	struct sockaddr_in 	sender;	
	socklen_t 			sender_len = sizeof(sender);
	u_int8_t 			buffer[IP_MAXPACKET];

	ssize_t packet_len = recvfrom (sockfd, buffer, IP_MAXPACKET, 0, (struct sockaddr*)&sender, &sender_len);
	if (packet_len < 0) {
		fprintf(stderr, "recvfrom error: %s\n", strerror(errno)); 
		return EXIT_FAILURE;
	}

	char sender_ip_str[20]; 
	inet_ntop(AF_INET, &(sender.sin_addr), sender_ip_str, sizeof(sender_ip_str));
	//printf ("Received IP packet with ICMP content from: %s\n", sender_ip_str);

	struct ip* 			ip_header = (struct ip*) buffer;
	ssize_t				ip_header_len = 4 * ip_header->ip_hl;
    struct icmp*        icmp_package = (struct icmp*)((uint8_t *)ip_header + ip_header_len);

    u_int8_t icmp_Type = icmp_package->icmp_type;
    u_int8_t icmp_Code = icmp_package->icmp_code;

    //We reached the destination point
    if(icmp_Type == 0 && icmp_Code == 0)
    {
        if(icmp_package->icmp_hun.ih_idseq.icd_id == (u_int16_t) PID && icmp_package->icmp_hun.ih_idseq.icd_seq == (u_int16_t) TTL)
        {
			inet_ntop(AF_INET, &(sender.sin_addr), response, 20);
            return 1;
        }
    }

    //Time Exceeded - we are not yet in the destination point
    if(icmp_Type == 11 && icmp_Code == 0)
    {
        struct ip*      ip_header_md = (struct ip *)((uint8_t *)icmp_package + 8);
		struct icmp*    icmp_package_md = (struct icmp *)((uint8_t *)ip_header_md + (*ip_header_md).ip_hl * 4);

        if(icmp_package_md->icmp_hun.ih_idseq.icd_id == (u_int16_t) PID && icmp_package_md->icmp_hun.ih_idseq.icd_seq == (u_int16_t) TTL)
        {
			inet_ntop(AF_INET, &(sender.sin_addr), response, 20);
            return 0;
        }
    }

	//Błąd
    return -1;
}
