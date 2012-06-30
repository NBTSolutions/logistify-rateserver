package co.logistify.api.test

class TestSuite {
    static List<Test> getTests() {
        return [
            new QueryTest(),
            new AdapterTest()
        ]
    }
}

