from pyproj.transformer import TransformerGroup
tg = TransformerGroup('EPSG:3844', 'EPSG:4326')
for t in tg.transformers:
    print(t.description)
