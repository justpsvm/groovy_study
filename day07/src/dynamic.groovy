

def takeHepler(helper){

	if(helper.metaClass.respondsTo(helper,'sendFlowers')){
		helper.sendFlowers();
	}
	
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

}


takeHepler(new Man())
takeHepler(new WoMan())
takeHepler(new Dog())






