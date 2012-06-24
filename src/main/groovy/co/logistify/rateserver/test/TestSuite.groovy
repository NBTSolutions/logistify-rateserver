package co.logistify.rateserver.test

class TestSuite {
    static List<Test> getTests() {
        return [
            new QueryTest(),
            new AdapterTest()
        ]
    }
}

