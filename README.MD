# Email Categorizer

This program will create a text list of every domain that has ever sent you an email. I used this program to create a list of every college that has sent me an email.

## Use

### Setup
First, get a copy of your email inbox in .mbox format. For gmail, this can be done using [Google Takeout](https://takeout.google.com/settings/takeout).

Once you have downloaded the file, rename it to "mail.mbox" without quotes and place it in the same folder as the .jar file.

**Currently the code only behaves properly if you download the source and run the main method from BlueJ.**

### Excluding certain terms
Then, make any edits needed to exclude.txt. If any email address contains any of the keywords placed in this file, they will not appear in the final result.

Place these keywords one-per-line.

This can be used to filter out emails from specific TLDs or with certain keywords in the domain.

An example exclude.txt (which is included with the program) is partially listed below. Any email address with terms listed here will *not* be included in the final result.

>.com
>
>.io
>
>nshss
>
>veracross
>
>collegeboard.org
>
>...

### Running the program
Finally, run "run.bat". This will take a few seconds, depending on how fast your comptuer is and how many emails you have. **Make sure that "export.txt", "exclude.txt", and "mail.mbox" are all present or the program will crash.** (For me, with an i5 and ~2000 total emails, it takes about 7 seconds.) The results will be located in export.txt, formatted like this:
>`vanderbilt.edu, seen 5 time(s).`
>
>`cmu.edu, seen 12 time(s).`
>
>`colgate.edu, seen 7 time(s).`
>
>`rose-hulman.edu, seen 92 time(s)`
>
>...

## Contributing
This project was written using BlueJ. Please do not look at the code, it is some of the worst code I have ever written.
