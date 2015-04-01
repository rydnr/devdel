AcmslVisitor
  extends VoidVisitorAdapter<VisitorContext> {
    overrides visit(CompilationUnit unit, VisitorContext context) {
      uses Commons-Logging to print "Inside the visit method";
      writes a java source file {creates a JavaFile 



           
