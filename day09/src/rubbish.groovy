def writer = new FileWriter("/Users/kanshan/1.txt")

//writer.write('hello world')
//writer.close() 忘记调用

// writer.withWriter() { w -> 
// 	w.write('hello world')
// }

writer.withWriter { w -> 
	w.write('hello world')
}

