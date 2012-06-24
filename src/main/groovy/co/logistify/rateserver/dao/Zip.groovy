package co.logistify.rateserver.dao

import javax.persistence.Id

class Zip {
    @Id String zip;
    String city
    String state
    String latitude
    String longitude
}


