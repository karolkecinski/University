#ifndef icmp_send_h
#define icmp_send_h

#include <netinet/ip.h>
#include <netinet/ip_icmp.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <assert.h>

u_int16_t compute_icmp_checksum (const void *buff, int length);

ssize_t send_package(int sockfd, struct sockaddr_in recipient, int TTL, int PID);

#endif