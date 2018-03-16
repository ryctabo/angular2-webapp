# RAML Documentation project

The documentation within the backend project is handled with the [RAML](http://raml.org/ "RAML Official Page") version [1.0](https://github.com/raml-org/raml-spec/blob/master/versions/raml-10/raml-10.md/ "RAML Specification Language"), which is a modeling language for APIs that allows us to document the project easily and quickly.

To compile, validate and generate the documentation we use a component called [raml2html](https://www.npmjs.org/package/raml2html), in the following paragraphs we will take the steps to be able to use it.

## Installation

To install raml2html you need [NPM](https://www.npmjs.com/), after having this dependency then you can execute the following command:

```
$ npm install -g raml2html
```

To test that we have already installed raml2html we can execute the help command, and so we immediately see that it offers us raml2html as a command.

```
$ raml2html -h
```

Finally, within the project we are currently using version 6.3.0, we can view the version with:

```
$ raml2html --version
```

## Compile, Validate and Generate HTML

In order to visualize the work that we are doing with the documentation of RAML we have several options, which we can see inside the aids that gives us the command raml2html.

Examples:

```
$ raml2html main.raml > index.html
$ raml2html -i main.raml -o index.html
$ raml2html --input main.raml --output index.html
```

However, with executing one of these commands we do not verify that the documentation we generate is fine, since it is not validated that the RAML documentation provided follows the specifications provided by the modeling language.

In order to verify and validate the documentation we need to place an additional parameter within the command that we make, this would be `--validate` or in its shortened version `-v`.

For example:

```
$ raml2html -v main.raml > index.html
```

## To End

Within our project we are placing the main files in the following routes:

```

-webapi
|--docs/
|  |--main.raml
|--src/
|  |--main/
|  |  |--webapp/
|  |  |  |--index.html
```

Files:
- **main.raml**: Main documentation file
- **index.html**: Generated HTML document

Thanks.
