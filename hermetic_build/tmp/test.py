import filecmp

try:
    print(filecmp.ndiff(["line1", "line2"], ["line1", "line3"]))
    print("filecmp.ndiff() exists!")
except AttributeError:
    print("filecmp.ndiff() does not exist.")