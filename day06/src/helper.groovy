

def takeHepler(helper){
	helper.sendFlowers();
}

class Man{

	def sendFlowers(){
		println "男人送花"
	}

}

class WoMan{

	def sendFlowers(){
		println "女人送花"
	}

}


class Dog{

	def sendFlowers(){
		println "小狗送花"
	}

}


takeHepler(new Man())
takeHepler(new WoMan())
takeHepler(new Dog())







