def str = "hello"

println str[0]

try{
	str[0] = 'w'
}catch(Exception ex){
	println ex
}
