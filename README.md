### stroke-kg (MVP)

Build a tiny knowledge graph for stroke rehabilitation from research PDFs.

- Parse PDFs → plain text
- Extract entities: Outcomes (FMA-UE, ARAT, BBT, BBS, 10MWT) and Interventions (VR, Conventional, Robotics, FES). Synonym: "virtual reality" → "VR".
- Detect relation: (VR|virtual reality) … improv* … (Outcome) → IMPROVES with confidence=0.6
- Load into Neo4j via MERGE (idempotent)
- FastAPI: `/top_interventions?outcome=FMA-UE&min_conf=0.5`

#### Setup
1) Create `.env` based on `.env.example` and set Neo4j credentials.
2) Install deps:
```bash
pip install -r requirements.txt
```
3) Start API:
```bash
uvicorn app:app --reload --host 0.0.0.0 --port 8000
```
4) Ingest PDFs (loads nodes/edges):
```bash
python ingest.py /path/to/pdfs
```

#### Notes
- Minimal regex/keyword extraction, not NLP-heavy.
- Safe to re-run ingestion; uses MERGE and uniqueness constraints.
