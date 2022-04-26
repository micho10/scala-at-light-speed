package demolambda

/**
  * How to Deploy Scala Code to AWS Lambda
  * https://blog.rockthejvm.com/lambda/
  *
  * IntelliJ IDEA
  * =============
  *
  * File -> Project Structure...
  *   Artifacts -> '+' JAR -> From modules with dependencies
  *     Fill in:
  *       - Module
  *       - Main Class
  *       - JAR files from libraries
  *          + extract to the target JAR (check)
  *   Apply -> OK
  *
  * Building the project:
  *
  * Build -> Build artifacts...
  *   Select defined artifact from pop up -> build
  *   Output:
  *     out -> <artifact.jar> -> <artifact.jar>
  *
  * Upload generated jar file to the cloud service
  */
class DemoLambda:

  def execute(): Int =
    println("I'm running from AWS!")
    42
