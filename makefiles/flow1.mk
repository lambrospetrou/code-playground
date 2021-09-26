MAKEFILE_NAME = flow1.mk
DATAFILES = $(wildcard data/*.json)

.PHONY: all,boom

all: boom

# Trigger a recursive `make` for the target `datafiles_all`, which depends on the data files to process.
# The critical argument is `--always-make` which will force the run all the time, 
# otherwise `make` will not do anything since the data files are not modified!
boom:
	$(MAKE) datafiles_all --always-make -f $(MAKEFILE_NAME)

# A target that depends on all the data files in order to force their processing.
# <finishprocessing>
datafiles_all: $(DATAFILES)
	@echo :: 'datafiles_all' finished!

# The target that will process each file. Sleeping intermittently just to simulate busy work.
# `processfile <F>`
data/%.json:
	@echo "processing single file:" $@
	@cat $@

