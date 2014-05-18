import patmat.Huffman._

object playaround {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(59); val res$0 = 
	List() == Nil;System.out.println("""res0: Boolean = """ + $show(res$0));$skip(19); val res$1 = 
	singleton(List());System.out.println("""res1: Boolean = """ + $show(res$1));$skip(16); val res$2 = 
	singleton(Nil);System.out.println("""res2: Boolean = """ + $show(res$2));$skip(140); val res$3 = 
	singleton(List(Fork(
		Fork(
			Leaf('c', 2),
			Leaf('d', 1),
			string2Chars("cd"),
			3),
		Leaf('e', 1),
		string2Chars("cde"),
		4)));System.out.println("""res3: Boolean = """ + $show(res$3));$skip(47); 
		
	val t = times(string2Chars("hello world"));System.out.println("""t  : List[(Char, Int)] = """ + $show(t ));$skip(25); val res$4 = 
  makeOrderedLeafList(t);System.out.println("""res4: List[patmat.Huffman.Leaf] = """ + $show(res$4));$skip(78); 
	val combo = combine(combine(List(Leaf('c', 1), Leaf('a', 2), Leaf('b', 2))));System.out.println("""combo  : List[patmat.Huffman.CodeTree] = """ + $show(combo ));$skip(18); val res$5 = 
	singleton(combo);System.out.println("""res5: Boolean = """ + $show(res$5));$skip(14); val res$6 = 
	combine(Nil);System.out.println("""res6: List[patmat.Huffman.CodeTree] = """ + $show(res$6));$skip(17); val res$7 = 
	combine(List());System.out.println("""res7: List[patmat.Huffman.CodeTree] = """ + $show(res$7));$skip(29); val res$8 = 
	combine(List(Leaf('a', 1)));System.out.println("""res8: List[patmat.Huffman.CodeTree] = """ + $show(res$8));$skip(68); val res$9 = 
	combine(List(Fork(Leaf('e', 1), Leaf('a', 1), List('e', 'a'), 2)));System.out.println("""res9: List[patmat.Huffman.CodeTree] = """ + $show(res$9))}
}
