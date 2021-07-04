TARGET_EXEC ?= compilo

CAML := ocamlc
BUILD_DIR ?= .
SRC_DIRS ?= .

SRCS := CLessType.ml Tools.ml CLessGram.ml CLessLex.ml PrintC.ml PrintAST.ml\
	Generate.ml compilo.ml 
#$(shell find $(SRC_DIRS) -name '*.ml')
OBJS := $(SRCS:.ml=.cmo)
DEPS := $(OBJS:.cmo=.d)

INC_DIRS := $(shell find $(SRC_DIRS) -type d)
INC_FLAGS :=
LDFLAGS := unix.cma

CPPFLAGS ?= $(INC_FLAGS)

$(BUILD_DIR)/$(TARGET_EXEC): $(OBJS)
	$(CAML) $(LDFLAGS) $(OBJS) -o $@ 

#Generate.cmo: Generate.ml
#	$(CAML) -ppx ./ppx_asm $(CPPFLAGS) -c $< -o $@	

# ml source
%.cmo: %.ml
#	$(MKDIR_P) $(dir $@)
	$(CAML) $(CPPFLAGS) -c $< -o $@
%cmi: %mli
	$(CAML) $<

.PHONY: clean

package:
	zip -r compilo.zip ./

clean:
	$(RM) $(BUILD_DIR)/*.cmo
	$(RM) $(BUILD_DIR)/*.cmi
	$(RM) $(TARGET_EXEC)

-include $(DEPS)

MKDIR_P ?= mkdir -p

CLessGram.cmo : CLessType.cmo CLessGram.cmi
CLessGram.cmx : CLessType.cmx CLessGram.cmi
CLessLex.cmo : CLessType.cmo CLessGram.cmi
CLessLex.cmx : CLessType.cmx CLessGram.cmx
Generate.cmo : CLessType.cmo Tools.cmo Generate.cmi
Generate.cmx : CLessType.cmx Tools.cmx Generate.cmi
PrintAST.cmo : CLessType.cmo
PrintAST.cmx : CLessType.cmx
PrintC.cmo : CLessType.cmo
PrintC.cmx : CLessType.cmx
compilo.cmo : PrintC.cmo PrintAST.cmo \
    Generate.cmi CLessType.cmo
compilo.cmx : PrintC.cmx PrintAST.cmx \
    Generate.cmx CLessType.cmx
