import re
from typing import Dict, List, Set

OUTCOMES = ["FMA-UE", "ARAT", "BBT", "BBS", "10MWT"]
INTERVENTIONS = ["VR", "Conventional", "Robotics", "FES"]
SYNONYMS = {"virtual reality": "VR"}

_sentence_split = re.compile(r"(?<=[.!?])\s+")
_word_boundary = lambda s: re.compile(rf"(?i)(?<![\w-]){re.escape(s)}(?![\w-])")


def _find_outcomes(sentence: str) -> Set[str]:
    found = set()
    for o in OUTCOMES:
        if _word_boundary(o).search(sentence):
            found.add(o)
    return found


def _find_interventions(sentence: str) -> Set[str]:
    lower = sentence.lower()
    found = set()
    for phrase, canon in SYNONYMS.items():
        if phrase in lower:
            found.add(canon)
    for intr in INTERVENTIONS:
        if _word_boundary(intr).search(sentence):
            found.add(intr)
    return found


def extract_entities_and_relations(text: str) -> Dict[str, List[Dict]]:
    nodes: Dict[str, Set[str]] = {"Intervention": set(), "Outcome": set()}
    edges: List[Dict] = []
    if not text:
        return {"nodes": [], "edges": []}

    sentences = _sentence_split.split(text.replace("\n", " "))
    for sent in sentences:
        if not sent.strip():
            continue
        outcomes = _find_outcomes(sent)
        interventions = _find_interventions(sent)
        for o in outcomes:
            nodes["Outcome"].add(o)
        for i in interventions:
            nodes["Intervention"].add(i)

        lower = sent.lower()
        if ("vr" in lower or "virtual reality" in lower) and "improv" in lower and outcomes:
            for o in outcomes:
                edges.append({
                    "type": "IMPROVES",
                    "start_label": "Intervention",
                    "start": "VR",
                    "end_label": "Outcome",
                    "end": o,
                    "props": {"confidence": 0.6},
                })

    node_list: List[Dict] = []
    for label, names in nodes.items():
        for name in sorted(names):
            node_list.append({"label": label, "name": name})

    return {"nodes": node_list, "edges": edges}
