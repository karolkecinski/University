//Karol Kęciński, nr ind. 315550
#include "icmp_send.h"

u_int16_t compute_icmp_checksum (const void *buff, int length)
{
	u_int32_t sum;
	const u_int16_t* ptr = buff;
	assert (length % 2 == 0);
	for (sum = 0; length > 0; length -= 2)
		sum += *ptr++;
	sum = (sum >> 16) + (sum & 0xffff);
	return (u_int16_t)(~(sum + (sum >> 16)));
}

ssize_t send_package(int sockfd, struct sockaddr_in recipient, int TTL, int PID)
{
    struct icmp header;
    header.icmp_type = ICMP_ECHO;
    header.icmp_code = 0;
    header.icmp_hun.ih_idseq.icd_id = PID;
    header.icmp_hun.ih_idseq.icd_seq = TTL;
    header.icmp_cksum = 0;
    header.icmp_cksum = compute_icmp_checksum ((u_int16_t*)&header, sizeof(header));

    ssize_t bytes_sent = sendto(
        sockfd,
        &header,
        sizeof(header),
        0,
        (struct sockaddr*)&recipient,
        sizeof(recipient)
    );

    return bytes_sent;
}
