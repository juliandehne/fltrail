package uzuzjmd.competence.persistence;

/**
 * Created by dehne on 22.09.2017.
 */

// Als Orientierung für die neue Implementierung der SQL-Storage
public abstract class SimilarityNQMStorage implements SimilarityAlgorithmStorage {


    /*@Override
    public void createOrUpdateBatchSimilarityMeasure(
            Competence competence, Double priority, SimilarityNode ... similarityNodeList){
        if (similarityNodeList != null) {
            for (SimilarityNode similarityNode : similarityNodeList) {
                try {
                    //competence.deleteEdgeWith(rangeNode, Edge.SimilarityMeasureOf);
                    similarityNode.persist();
                    createOrUpdateSpecialSimilarityMeasure(competence, priority, similarityNode);
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public void createOrUpdateBatchSimilarityMeasure(
            Competence competence, Double priority, List<SimilarityNode> similarityNodeList) {
        createOrUpdateBatchSimilarityMeasure(competence,priority,similarityNodeList.toArray(new SimilarityNode[0]));
    }

    public void createSimilarityNode(Competence competence, Double priority, SimilarityNode similarityNode)
            throws Exception {
        String query = createSimilarityMeasureQuery(competence, priority, similarityNode);
        issueNeo4JRequestStrings(query);
    }

    public String createSimilarityMeasureQuery(Competence competence, Double priority, SimilarityNode similarityNode) {
        StringBuilder builder = new StringBuilder();
        builder.append("MATCH (c:SimilarityNode{id:'" + similarityNode.getId() + "'})");
        builder.append("MATCH (n:Competence{id:'" + competence.getId() + "'})");
        builder.append("CREATE UNIQUE (c)-[r1:SimilarityMeasureOf {weight:" + priority + "}]->(n) ");
        builder.append("return c, r1, n ");
        return builder.toString();
    }

    public void createOrUpdateSpecialSimilarityMeasure(
            Competence competence, Double priority, SimilarityNode similarityNode) {
        try {
            if (!similarityNode.hasEdge(Edge.SimilarityMeasureOf, competence)) {
                createSimilarityNode(competence, priority, similarityNode);
            } else {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("MATCH (c:SimilarityNode{id:'" + similarityNode.getId() + "'}) ");
                stringBuilder2.append("MATCH (n:Competence{id:'" + competence.getId() + "'}) ");
                stringBuilder2.append("MATCH (c)-[r1:SimilarityMeasureOf]->(n) ");
                stringBuilder2.append(" set r1.weight = (r1.weight + " + priority +")");
                stringBuilder2.append(" return c, n, r1");
                String query = stringBuilder2.toString();
                issueNeo4JRequestStrings(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<ArrayList<Object>> getSimilarNodesResultSet(String competence) {
        String query = getSimilarNodesQuery(competence);
        ArrayList<ArrayList<Object>> result = getArrayListsArrayListObjects(query);
        if (result != null)
            return result;
        return null;
    }

    public String getSimilarNodesQuery(String competence) {
        StringBuilder builder = new StringBuilder();
        builder.append("MATCH(n)<-[r2:SimilarityMeasureOf]-(s)-[r1:SimilarityMeasureOf]->");
        builder.append("(p:Competence{id:'" + competence + "'})");
        builder.append("return n.id,r2.weight,s.id,r1.weight,p.id");
        return builder.toString();
    }

    *//**
     * returns rows of userid, userid, similaritymeasure
     * @param projectId
     * @return
     * @throws Exception
     *//*
    public ArrayList<ArrayList<Object>> getSimilaritesForProject(String projectId) throws Exception {
        String query =
                "MATCH (n:CourseContext{id:'"+projectId+"'})-[r1:CourseContextOfCompetence]->(c) " +
                "MATCH (c)-[r2:SimilarTo]->(c2) " +
                "MATCH (u1:User)-[r3:InterestedInCompetence]->(c) " +
                "MATCH (u2:User)-[r4:InterestedInCompetence]->(c2) where u1.id <> u2.id and n.id <> 'university'"  +
                "return u1.id, u2.id, sum(toFloat(r2.weight))";
        ArrayList<ArrayList<Object>> result = getArrayListsArrayListObjects(query);
        return result;
    }

    public ArrayList<String> getAllUserForProject(String projectId) throws Exception {
        String query =
                "MATCH (n:CourseContext{id:'"+projectId+"'})-[r1:CourseContextOfUser]->(u) " +
                        "where  n.id <> 'university'"  +
                        "return u.id";
        return issueNeo4JRequestStrings(query);
    }
*/
}
