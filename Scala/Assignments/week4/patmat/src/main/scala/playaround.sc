import patmat.Huffman._

object playaround {
	List() == Nil                             //> res0: Boolean = true
	singleton(List())                         //> res1: Boolean = false
	singleton(Nil)                            //> res2: Boolean = false
	singleton(List(Fork(
		Fork(
			Leaf('c', 2),
			Leaf('d', 1),
			string2Chars("cd"),
			3),
		Leaf('e', 1),
		string2Chars("cde"),
		4)))                              //> res3: Boolean = true
		
	val t = times(string2Chars("hello world"))//> t  : List[(Char, Int)] = List((d,1), (r,1), (w,1), ( ,1), (o,2), (l,3), (e,1
                                                  //| ), (h,1))
  makeOrderedLeafList(t)                          //> res4: List[patmat.Huffman.Leaf] = List(Leaf(d,1), Leaf(r,1), Leaf(w,1), Leaf
                                                  //| ( ,1), Leaf(e,1), Leaf(h,1), Leaf(o,2), Leaf(l,3))
	val combo = combine(combine(List(Leaf('c', 1), Leaf('a', 2), Leaf('b', 2))))
                                                  //> combo  : List[patmat.Huffman.CodeTree] = List(Fork(Fork(Leaf(c,1),Leaf(a,2),
                                                  //| List(c, a),3),Leaf(b,2),List(c, a, b),5))
	singleton(combo)                          //> res5: Boolean = true
	combine(Nil)                              //> res6: List[patmat.Huffman.CodeTree] = List()
	combine(List())                           //> res7: List[patmat.Huffman.CodeTree] = List()
	combine(List(Leaf('a', 1)))               //> res8: List[patmat.Huffman.CodeTree] = List(Leaf(a,1))
	combine(List(Fork(Leaf('e', 1), Leaf('a', 1), List('e', 'a'), 2)))
                                                  //> res9: List[patmat.Huffman.CodeTree] = List(Fork(Leaf(e,1),Leaf(a,1),List(e, 
                                                  //| a),2))
}