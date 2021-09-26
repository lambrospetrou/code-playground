default: t7

t1:
	@echo "t1"
	@echo "t1-content-output" > f1.txt
	@echo "t1 output file written!"

t2:
	@echo "t2"

t3: t1 t6
	@echo "t3"
	@cat f1.txt
	@echo "t1 output file printed!"

t4_5_setup:
	@rm -f /tmp/comm.fifo
	@mkfifo /tmp/comm.fifo

t4: t4_5_setup
	@echo "t4"
	@cat /usr/share/dict/words > /tmp/comm.fifo

t5: t4_5_setup
	@echo "t5"
	@echo "Total lines: " && wc -l < /tmp/comm.fifo

t6: t2 t4 t5
	@echo "t6"

t7: t3 t6
	@echo "t7"
