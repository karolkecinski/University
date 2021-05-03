#ifndef icmp_receive_h
#define icmp_receive_h

#include <netinet/ip.h>
#include <netinet/ip_icmp.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <sys/time.h>
#include <unistd.h>

int receive_from(int sockfd, int PID, int TTL, char response[20]);

void print_as_bytes (unsigned char* buff, ssize_t length);

void print_row(int RCV_NUM, int avg, int TTL, char responses[3][20]);

int receive(int sockfd, int PID, int TTL);
#endif