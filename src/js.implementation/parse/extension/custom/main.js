'use strict'

function convert() {
    var asciidoctor = Asciidoctor();

    asciidoctor.Extensions.register(function () {
        this.block(function () {
            const self = this;
            self.named('action');
            self.onContext('paragraph');
            self.process(function (parent, reader) {
                const content = "<input type=\"button\" class=\"quick-guide-action\" value=\"guide che action button\"/>";
                return self.createBlock(parent, 'pass', [content]);
            });
        });
    });

    var text = document.getElementById("content-widget").innerHTML;

    var begin = new Date().getTime();

    const html = asciidoctor.convert(text);

    var timeDiff = new Date().getTime() - begin;

    console.log(timeDiff);

    document.getElementById("result-container").innerHTML = html;
}