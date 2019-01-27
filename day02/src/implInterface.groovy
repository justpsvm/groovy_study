interface UserService{
	def add()
	def delete()
	def queryAll()
}

def operation(service){
	service.add()
	service.delete()
	service.queryAll()
}

userService = [
	add: {println 'call add'},
	delete: {println 'call delete'}
]

operation(userService as UserService)




