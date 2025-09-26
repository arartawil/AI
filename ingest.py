import os
import sys
from typing import List
from stroke_kg.pdf_utils import parse_pdf_to_text
from stroke_kg.extract import extract_entities_and_relations
from stroke_kg.neo4j_loader import Neo4jLoader


def iter_pdfs(root: str) -> List[str]:
    paths = []
    for dirpath, _, filenames in os.walk(root):
        for f in filenames:
            if f.lower().endswith(".pdf"):
                paths.append(os.path.join(dirpath, f))
    return paths


def main(pdf_dir: str) -> None:
    loader = Neo4jLoader()
    try:
        all_nodes = []
        all_edges = []
        for pdf in iter_pdfs(pdf_dir):
            text = parse_pdf_to_text(pdf)
            result = extract_entities_and_relations(text)
            all_nodes.extend(result["nodes"])
            all_edges.extend(result["edges"])
        seen = set()
        uniq_nodes = []
        for n in all_nodes:
            key = (n["label"], n["name"]) 
            if key not in seen:
                seen.add(key)
                uniq_nodes.append(n)
        loader.load(uniq_nodes, all_edges)
        print(f"Loaded {len(uniq_nodes)} nodes and {len(all_edges)} edges")
    finally:
        loader.close()


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python ingest.py /path/to/pdfs")
        sys.exit(1)
    main(sys.argv[1])
