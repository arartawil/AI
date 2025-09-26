from PyPDF2 import PdfReader


def parse_pdf_to_text(pdf_path: str) -> str:
    """Return concatenated text from a PDF using PyPDF2 (best-effort)."""
    try:
        reader = PdfReader(pdf_path)
        texts = []
        for page in reader.pages:
            try:
                texts.append(page.extract_text() or "")
            except Exception:
                continue
        return "\n".join(texts)
    except Exception:
        return ""
