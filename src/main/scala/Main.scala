object Main extends App {

  def main(): Unit = {
    val source = args(0)
    printAst(getAst(source))
  }

  def printAst(ast: String, openBracket: Char = '(', closeBracket: Char = ')', tabSpaces: Int = 2): Unit = {
    val sb = new StringBuilder
    var currentIndent = 0
    def indent = new String(Array.fill(currentIndent)(' '))

    for ((c, i) <- ast.zipWithIndex) c match {
      case '\n' => sb.append(c).append(indent)
      case `openBracket` =>
        sb.append(c)
        if (i == ast.length - 1 || ast.charAt(i + 1) != closeBracket) {
          sb.append('\n')
          currentIndent += tabSpaces
          sb.append(indent)
        }
      case `closeBracket` =>
        if (i == 0 || ast.charAt(i - 1) != openBracket) {
          sb.append('\n')
          currentIndent -= tabSpaces
          sb.append(indent)
        }
        sb.append(c)
      case _ => sb.append(c)
    }
    println(sb.toString())
  }

  def getAst(source: String): String = {
    val tokens = scalariform.lexer.ScalaLexer.tokenise(source, forgiveErrors = true).toArray
    val rawTree = new scalariform.parser.ScalaParser(tokens).compilationUnitOrScript().immediateChildren.head.toString
    rawTree.replace("\r", "").replace("\n", "")
        .replaceAll("""Token\(LPAREN,\(,\d+,\(\)""", "Token(LPAREN)")
        .replaceAll("""Token\(RPAREN,\),\d+,\)\)""", "Token(RPAREN)")
  }

  main()
}
