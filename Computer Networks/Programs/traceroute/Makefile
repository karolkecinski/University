FLAGS = -std=c17 -D_GNU_SOURCE -Wall -Wextra

traceroute: traceroute.o icmp_receive.o icmp_send.o
	gcc $(FLAGS) -o traceroute traceroute.o icmp_receive.o icmp_send.o

traceroute.o: traceroute.c traceroute.h icmp_receive.h icmp_send.h

icmp_receive.o: icmp_receive.c icmp_receive.h

icmp_send.o: icmp_send.c icmp_send.h

clean:
	$(RM) *.o

distclean:
	$(RM) traceroute *.o

.PHONY: clean
