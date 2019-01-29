
import groovy.transform.*


@Immutable
@Canonical
class Order{
	String orderNum
	Date createDate
}


def order = new Order("EORC210041321G",new Date())

//order.orderNum = '';

println order




