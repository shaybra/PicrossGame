JAVAC = javac

JAVA_FILES = $(wildcard *.java controller/*.java model/*.java view/*.java server/*.java)

CLASS_FILES = $(JAVA_FILES:.java=.class)

BUILD_DIR = build

all: $(addprefix $(BUILD_DIR)/,$(CLASS_FILES))

$(BUILD_DIR)/%.class: %.java
	mkdir -p $(@D)
	$(JAVAC) -d $(BUILD_DIR) $<

clean:
	rm -rf $(BUILD_DIR)
