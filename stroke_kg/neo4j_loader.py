import os
from typing import Dict, List
from neo4j import GraphDatabase
from dotenv import load_dotenv

load_dotenv()


class Neo4jLoader:
    def __init__(self) -> None:
        uri = os.getenv("NEO4J_URI", "bolt://localhost:7687")
        user = os.getenv("NEO4J_USER", "neo4j")
        password = os.getenv("NEO4J_PASSWORD", "neo4j")
        self.driver = GraphDatabase.driver(uri, auth=(user, password))
        self._ensure_constraints()

    def close(self) -> None:
        self.driver.close()

    def _ensure_constraints(self) -> None:
        queries = [
            (
                "CREATE CONSTRAINT IF NOT EXISTS FOR (n:Intervention) REQUIRE "
                "n.name IS UNIQUE"
            ),
            (
                "CREATE CONSTRAINT IF NOT EXISTS FOR (n:Outcome) REQUIRE "
                "n.name IS UNIQUE"
            ),
        ]
        with self.driver.session() as session:
            for q in queries:
                session.run(q)

    def load(self, nodes: List[Dict], edges: List[Dict]) -> None:
        with self.driver.session() as session:
            for n in nodes:
                label = n["label"]
                name = n["name"]
                session.run(f"MERGE (x:{label} {{name: $name}})", name=name)
            for e in edges:
                start_label = e["start_label"]
                end_label = e["end_label"]
                rel_type = e["type"]
                session.run(
                    (
                        f"MATCH (a:{start_label} {{name: $start}}) "
                        f"MATCH (b:{end_label} {{name: $end}}) "
                        f"MERGE (a)-[r:{rel_type}]->(b) "
                        f"SET r += $props"
                    ),
                    start=e["start"],
                    end=e["end"],
                    props=e.get("props", {}),
                )
