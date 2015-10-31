=== README ===

This is some notes for the MessageParserDemo application.

Please check the "architecture/architecture.png" for the general image.
Here are some detail: there are two modules:

* [app] - demo GUI.
Data is read from "/sdcard/messages.txt" and displayed on the list.
The parsed JSON result will be shown on the bottom EditText when click on each message.
It depends on the [parserlib] to parse JSON.

* [parserlib] - the parse core.
Parse process is done by using regular expression.
Each element (mention, emoticon, url) is separated in its own class so its data (Pattern) or special
processing (parse result validation) can be easily implemented.
All elements are extended from Element class, which implement the main parse process.
The library also applied automation test using Android Test Framework.
