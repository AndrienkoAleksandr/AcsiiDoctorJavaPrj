package com.codenvy.example.java.processors;

import com.codenvy.example.java.processors.UriIncludeProcessor;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.junit.Test;

import java.util.HashMap;

import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.junit.Assert.assertEquals;

public class UriIncludeProcessorTest {
    private static final String ASCII_DOC = "= Example of URI\n" +
            "\n" +
            ".Gemfile\n" +
            "[source,ruby]\n" +
            "----\n" +
            "include::https://raw.githubusercontent.com/asciidoctor/asciidoctor/master/Gemfile[]\n" +
            "----";

    private Asciidoctor asciidoctor = create();

    @Test
    public void shouldParseContentWithoutTreeProcessor() {
        String outPut = asciidoctor.convert(ASCII_DOC, new HashMap<>());

        String expectedOutPut = "<div class=\"listingblock\">\n" +
                "<div class=\"title\">Gemfile</div>\n" +
                "<div class=\"content\">\n" +
                "<pre class=\"highlight\"><code class=\"language-ruby\" data-lang=\"ruby\">link:https://raw.githubusercontent.com/asciidoctor/asciidoctor/master/Gemfile[]</code></pre>\n" +
                "</div>\n" +
                "</div>";

        assertEquals(outPut, expectedOutPut);
    }

    @Test
    public void shouldParseContentWithTreeProcessor() {
        JavaExtensionRegistry extensionRegistry = this.asciidoctor.javaExtensionRegistry();

        extensionRegistry.includeProcessor(UriIncludeProcessor.class);

        String content = asciidoctor.convert(ASCII_DOC, new Options());

        String expectedContent = "<div class=\"listingblock\">\n" +
                "<div class=\"title\">Gemfile</div>\n" +
                "<div class=\"content\">\n" +
                "<pre class=\"highlight\"><code class=\"language-ruby\" data-lang=\"ruby\">source 'https://rubygems.org'# Look in asciidoctor.gemspec for runtime and development dependenciesgemspecgroup :development do  if (ruby_version = Gem::Version.new RUBY_VERSION) &lt; (Gem::Version.new '2.1.0')    if ruby_version &lt; (Gem::Version.new '2.0.0')      gem 'haml', '~&gt; 4.0.0'      if ruby_version &lt; (Gem::Version.new '1.9.3')        gem 'cucumber', '~&gt; 1.3.0'        gem 'nokogiri', '~&gt; 1.5.0'        gem 'slim', '~&gt; 2.1.0'        gem 'tilt', '2.0.7'      else        gem 'nokogiri', '~&gt; 1.6.0'        gem 'slim', '&lt;= 3.0.7'      end    else      gem 'nokogiri', '~&gt; 1.6.0'    end  elsif ruby_version &lt; (Gem::Version.new '2.2.0')    gem 'nokogiri', '~&gt; 1.7.0' if Gem::Platform.local =~ 'x86-mingw32' || Gem::Platform.local =~ 'x64-mingw32'  end  gem 'racc', '~&gt; 1.4.0' if RUBY_VERSION == '2.1.0' &amp;&amp; RUBY_ENGINE == 'rbx'end# enable this group to use Guard for continuous testing# after removing comments, run `bundle install` then `guard`#group :guardtest do#  gem 'guard'#  gem 'guard-test'#  gem 'libnotify'#  gem 'listen', :github =&gt; 'guard/listen'#endgroup :ci do  gem 'simplecov', '~&gt; 0.14.1'  if ENV['SHIPPABLE']    gem 'simplecov-csv', '~&gt; 0.1.3'    gem 'ci_reporter', '~&gt; 2.0.0'    gem 'ci_reporter_minitest', '~&gt; 1.0.0'    #gem 'ci_reporter_cucumber', '~&gt; 1.0.0'  endend</code></pre>\n" +
                "</div>\n" +
                "</div>";

        assertEquals(expectedContent, content);
    }
}
