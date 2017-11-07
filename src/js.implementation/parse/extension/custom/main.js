'use strict'

function convert() {
    var asciidoctor = Asciidoctor();
    asciidoctor.Extensions.register(function () {
        this.inlineMacro(function () {
            const self = this;
            self.named('che');
            self.process(function (parent, target, properties) {
                //todo with help target (which is equal to action id) and properties we can create and save IDE action
                var actionBtnText = Opal.hash_get(properties, "label");
                //return html action button content
                return "<input type=\"button\" class=\"quick-guide-action\" value=\"" + actionBtnText + "\"/>";
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