/*
                        DevDel

    Copyright (C) 2015-today  Jose San Leandro Armendariz
                              chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: DevDelGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: A ST-based generator which finds out which template to call
 *              by checking which annotations match the input text.
 *
 * Date: 2015/02/21
 * Time: 10:04
 *
 */
package org.acmsl.devdel;

/*
 * Importing StringTemplate (-rydnr) classes.
 */
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STAnnotatedGroup;
import org.stringtemplate.v4.STGroup;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A ST-based generator which finds out which template to call
 * by checking which annotations match the input text.
 * @author <a href="mailto:github@acmsl-sl.org">rydnr</a>
 * @since 3.0
 */
@ThreadSafe
public class DevDelGenerator {

    /**
     * The input text.
     */
    private final String input;

    /**
     * The ST groups.
     */
    private List<STAnnotatedGroup> groups = new ArrayList<STAnnotatedGroup>();

    /**
     * Creates a new {@code DevDelGenerator} instance.
     * @param input the input.
     */
    public DevDelGenerator(@NotNull final String input) {
        this.input = input;
    }

    /**
     * Loads a new {@link STGroup}.
     * @param group the group to import.
     */
    public void loadGroup(@NotNull final STGroup group) {
        groups.add(new STAnnotatedGroup(group));
    }

    /**
     * Generates the output based on the input and loaded templates.
     * @return the output.
     */
    @NotNull
    public String generate() {
        return generate(this.input, this.groups);
    }
    
    /**
     * Generates the output based on the input and loaded templates.
     * @param input the input.
     * @param groups the ST groups.
     * @return the output.
     */
    @NotNull
    protected String generate(@NotNull final String input, @NotNull final List<STAnnotatedGroup> groups) {

        @NotNull final String result;
        
        @Nullable ST template = null;
        
        for (@NotNull final STAnnotatedGroup group : groups) {
            template = group.getMatchingTemplate(input);
            if (template != null) {
                break;
            }
        }

        if (template == null) {
            result = "";
        } else {

            result = template.render();
        }

        return result;
    }
}
