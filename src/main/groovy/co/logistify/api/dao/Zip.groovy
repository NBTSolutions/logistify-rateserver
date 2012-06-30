package co.logistify.api.dao

import javax.persistence.Id

class Zip {
    @Id String zip;
    String city
    String state
    String latitude
    String longitude
}


