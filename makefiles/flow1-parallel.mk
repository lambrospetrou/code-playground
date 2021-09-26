MAKEFILE_NAME = flow1-parallel.mk
DATAFILES = $(wildcard data/*.json)

.PHONY: all,boom

default: boom

# Trigger a recursive `make` for the target `datafiles_all`, which depends on the data files to process.
# The critical argument is `--always-make` which will force the run all the time, 
# otherwise `make` will not do anything since the data files are not modified!
boom:
	$(MAKE) datafiles_all --always-make -f $(MAKEFILE_NAME)

# A trampoline target that depends on all the data files in order to force their processing.
datafiles_all: $(DATAFILES)
	@echo :: 'datafiles_all' finished!

data/a.json:
	@echo "processing slow file:" $@
	@sleep 2
	@echo still processing $@ ...
	@sleep 2
	@echo finished processing $@ ...

# The target that corresponds to each file.
data/%.json:
	@echo "processing single file:" $@
	@cat $@

