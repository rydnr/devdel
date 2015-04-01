package org.acmsl.devdel;

import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import org.acmsl.commons.utils.io.FileUtils;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.experimental.categories.Category;
import org.junit.Assert;
import org.junit.Test;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.io.IOException;

@RunWith(JUnit4.class)
// Meaning this is an integration test
@Category(Test.class)
public class CachingIsNewStrategyFactoryTest {

    @Test
    public void generation_logic_works_for_CachingIsNewStrategyFactory()
        throws IOException {

        @NotNull final STGroup stGroup = new STGroupFile("org/springframework/data/support/CachingIsNewStrategyFactory.stg");

        @NotNull final StringBuilder aux = new StringBuilder();
        
        @NotNull final String article =
            FileUtils.getInstance().readFile(
                "src/main/resources/org/springframework/data/support/CachingIsNewStrategyFactory.org",
                Charset.defaultCharset());

        Assert.assertNotNull(article);

        for (@NotNull final String line : article.split("\n")) {
            @NotNull final DevDelGenerator generator = new DevDelGenerator(line);

            generator.loadGroup(stGroup);
        
            aux.append(generator.generate());
        }

        @NotNull final String outcome = aux.toString();
        
        Assert.assertTrue(outcome.length() > 0);
        Assert.assertEquals(
            "public class CachingIsNewStrategyFactory implements IsNewStrategyFactory {"
            +     "private final Map<Class<?>, IsNewStrategy> cache = new ConcurrentHashMap<Class<?>, IsNewStrategy>();"
            +     "private final IsNewStrategyFactory delegate;"
            +     "/**"
            +      "* Creates a new {@link CachingIsNewStrategyFactory} delegating to the given {@link IsNewStrategyFactory}."
	        +      "* @param delegate must not be {@literal null}."
	        +      "*/"
            +     "public CachingIsNewStrategyFactory(IsNewStrategyFactory delegate) {"
		    +         "Assert.notNull(delegate, \"IsNewStrategyFactory delegate must not be null!\");"
            +         "this.delegate = delegate;"
	        +     "}"
	        +     "/**"
	        +      "* @see org.springframework.data.mapping.model.IsNewStrategyFactory#getIsNewStrategy(java.lang.Class)"
            +      "*/"
	        +     "public IsNewStrategy getIsNewStrategy(Class<?> type) {"
		    +         "IsNewStrategy strategy = cache.get(type);"
    		+         "if (strategy != null) {"
            +             "return strategy;"
		    +         "}"
            +         "IsNewStrategy isNewStrategy = delegate.getIsNewStrategy(type);"
            +         "cache.put(type, isNewStrategy);"
            +         "return isNewStrategy;"
            +     "}"
            + "}",
            outcome.replaceAll("\\s+", "\\s"));
    }
}
