# Datacap Pre-Interview Project

## Quick Start

### Important Information

Programming Language: Java

IDE: IntelliJ IDEA 2020.3.2

#### SDK Information

Java Version: 8

Source: AdoptOpenJDK

JVM: HotSpot

JDK Version: 1.8.0_292

### Run The Project

I've included a JAR file in my submission (found in the `builds` folder) that can be executed with the following command (run with the environment listed above):

```java -jar Datacap.jar TransactionSales.xml TransactionVoids.xml```

The arguments `Transaction_Sales.xml` and `Transaction_Voids.xml` represent the transaction file names that are taken as an input to the program. You can change those files and/or add more, as my implementation does not have a limit on the number of files that can be processed. They also do not need to contain only sales or only voids. These files must be present in the directory where the JAR is being executed, or a path must be provided.

The console will output any problems encountered when running the program, such as voids that have a mismatch with sales. The console will also output the rankings at the end, just in case the GUI fails to open.

### The Source Code

The source code is located within the `src` folder and can be viewed and compiled with any Java IDE (though my personal preference is IntelliJ IDEA). When running with the IDE, ensure that the command line arguments are set to be the input files for the program. The files should be located at the base directory of the project.

## Introduction

Hi, my name is Damien Brocato. Allow me to extend my thanks for reviewing my submission here.

I'd like to preface by stating that this README is less of a documentation and more of a space for me to explain my thought and design processes for those who are interested. I'll endeavor to keep my explanations brief. However, if you wish, you may forsake this file entirely and instead look at the code alone, which should suffice as well.

It took me about one full workday to complete this project in a way that I was pleased with. I wish to emphasize that while the spec is relatively simple and straightforward, I took care to write code that is representative of my skills and style while keeping it readable and well-commented. I also made an effort to learn new things that I hadn't done before (more on that later). Had I made a dash towards the minimum viable product for submission, it would likely have taken me about half that time, perhaps less.

Thank you again for taking the time to check out my submission, and I look forward to hearing from you!

## Remarks

### Why I Chose Java

The project states that I could choose any language to write this program in, but maintained a preference for Java, C#, and C++. While I could most definitely do this project in any of those three languages, I chose Java for a few reasons.

First, I considered the fact that this project was to be run on someone else's machine in an environment that was not of my choosing. Java is (in most cases) an easy-to-use cross-platform language with a convenient and portable build structure, with the only caveat being that I must include the version upon which I developed the application. I took advantage of this by including a JAR file for running the program out of the box, rather than requiring the user to compile it themselves.

Second, I find that Java is easier to work with when building a short-term solution, particularly when compared to C++. That's not to say that Java can't scale, as it certainly can. In the same sense, I'm also not claiming that Java is flawless; it certainly has it's downsides.

### What I Had to Learn

The majority of my implementation was done using skills I already had. Data structures, software design, lambdas, and optimization are skills I acquired through both the pursuit of my degree and personal projects alike. However, there were two fields which I had little experience in: XML file parsing and GUI programming. I did my research on both of these topics before following through with my theory of implementation.

XML file parsing was unfamiliar at first, but quickly bore striking resemblance to JSON file parsing, a skill I developed while working on my Minecraft mod. With some brief debugging, I managed to work it out just fine.

GUI programming was another obstacle, and one that did not translate easily into anything I already knew. I followed the documentation and looked up examples on the web, and was ultimately able to make a simple functional display. I now understand a few of the core concepts of how Swing GUIs operate, but I can't claim to be an expert. My use of the GUI also leaves a lot to be desired, I will admit. The standing point is, however, that I was able to quickly learn this new skill to satisfy the "extra credit" included on the spec.

### Design Choices

While the scope described by the specification is small, I made it a priority to demonstrate how my code might be scalable into larger and more lenient operation. The simplest example is the fact that I allow the input of any number of input files. Those input files are also not required to contain only sale-type or only void-type transactions; it can be a mix of both.

More specifically, I designed the system such that more rules can be defined beyond the single pattern described in the spec, so long as the factors are included in the Transaction object. See `PaymentProcessorRegistry.java` for an example.

In addition, I made it simple to add more Payment Processors and have them automatically included in the execution of the program. That can also be found in `PaymentProcessorRegistry.java`, and seen used in `XMLProcessor.java`.

I also designed it so that multiple rules can be applied to a single transaction, if desired. Whether or not that happens is up to the user, as they have the ability to define rule validations such that the correct rules get applied to the correct transactions. Adding new rules to Payment Processors can be done in `PaymentProcessorRegistry.java`.

Finally, I treated the statement that the input files' data was to be deserialized into objects as a design requirement, and did just that. I deserialize the files' data into `Transaction` objects, which are then used for void checking and in the final calculation.

### A Note on Lambdas and Coding Standards

You will likely notice that I use lambdas extensively throughout this project. Over the past few months, I've become very comfortable with them and find them to be naturally self-documenting. There are likely those who disagree, and I have no problem with that.

I'm making this note to say that I always remain conscious of the coding standards put in place by existing software. Since this is software I wrote myself, I used my preferences. If I were working on a shared repository, I would adhere to the coding standards that exist there unless I was asked to do otherwise.

Another small note is that I didn't adhere to Javadoc formatting standards. More specifically, I chose to forego the @param annotations, and the ones like it. I did this purely to expedite the programming process, and would be more careful to include them when necessary.

### Possibly Mismatched Results

If my results seem slightly off (by a few hundreths) of what you expected, I would suggest checking out my comments in `PaymentProcessor.java`. There I explain that a bit of brute-forcing was required to make an accurate value. When thinking about it, I compared it to gas pump prices, which also deal in fractions of a cent: the price of the gas you bought is determined at the end of your transaction, not at the end of the day when the computer totals all the sales made for the day. This may have been an unnecessary detail, but I opted to include it nevertheless.
