
def getText(path,call){
	def input
	try{
		input = new FileInputStream(new File(path))
		call(input)
	}finally{
		if(input != null){
			input.close()
		}
	}

}


getText("/Users/kanshan/Downloads/1.txt"){input -> 
	byte[] byts = new byte[input.available()]
	input.read(byts)
	println new String(byts)
}




