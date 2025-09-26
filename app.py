from typing import List, Dict
from fastapi import FastAPI, Query
from stroke_kg.neo4j_loader import Neo4jLoader

app = FastAPI(title="stroke-kg")


@app.get("/top_interventions")
def top_interventions(outcome: str = Query(...), min_conf: float = Query(0.5)) -> List[Dict]:
    loader = Neo4jLoader()
    try:
        query = (
            "MATCH (i:Intervention)-[r:IMPROVES]->(o:Outcome {name: $outcome}) "
            "WHERE coalesce(r.confidence, 0) >= $min_conf "
            "RETURN i.name AS intervention, count(r) AS rels, "
            "max(coalesce(r.confidence,0)) AS max_conf "
            "ORDER BY rels DESC, max_conf DESC"
        )
        with loader.driver.session() as session:
            result = session.run(query, outcome=outcome, min_conf=min_conf)
            return [dict(record) for record in result]
    finally:
        loader.close()
