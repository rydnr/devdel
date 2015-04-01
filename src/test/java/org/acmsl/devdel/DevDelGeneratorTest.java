package org.acmsl.devdel;

import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;
import org.junit.Test;

import org.jetbrains.annotations.NotNull;

@RunWith(JUnit4.class)
public class DevDelGeneratorTest {

    @Test
    public void generation_logic_works_for_a_single_paragraph() {

        @NotNull final STGroup stGroup =
            new STGroupString(
                  "@MyKeyword(\"A (?<type>.*) paragraph.\")\n"
                + "par(type) ::= <<\n"
                + "A \\<b><type>\\</b> paragraph.\n\n"
                + ">>\n");

        @NotNull final DevDelGenerator generator = new DevDelGenerator("A very simple paragraph.");

        generator.loadGroup(stGroup);
        
        @NotNull final String outcome = generator.generate();
        
        Assert.assertNotNull(outcome);
        Assert.assertEquals("A <b>very simple</b> paragraph.\n", outcome);
    }
}
