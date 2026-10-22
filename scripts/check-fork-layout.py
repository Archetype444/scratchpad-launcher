#!/usr/bin/env python3
"""Fail a sync before publishing if upstream removed the fork's layout contract."""
from pathlib import Path
import re
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
ANDROID = '{http://schemas.android.com/apk/res/android}'


def check(root=ROOT):
    java = root / 'app/src/main/java/app/olauncher'
    constants = (java / 'data/Constants.kt').read_text()
    for name, value in [('MAX_HOME_APPS', 15), ('HOME_APP_COLUMNS', 3), ('HOME_APP_ROWS', 5)]:
        assert re.search(rf'const val {name}\s*=\s*{value}\b', constants), name

    layouts = list((root / 'app/src/main/res').glob('layout*/fragment_home.xml'))
    assert layouts
    for path in layouts:
        layout = ET.parse(path).getroot()
        grid = next(e for e in layout.iter() if e.get(ANDROID + 'id') == '@+id/homeAppsGrid')
        assert len(grid) == 3, f'{path}: expected three columns'
        for col, column in enumerate(grid):
            assert len(column) == 5, f'{path}: expected five rows'
            assert column.get(ANDROID + 'layout_weight') == '1'
            for row, slot in enumerate(column):
                position = col * 5 + row + 1
                assert slot.get(ANDROID + 'id') == f'@+id/homeApp{position}'
                assert slot.get(ANDROID + 'tag') == str(position)

    home = (java / 'ui/HomeFragment.kt').read_text()
    assert all(f'binding.homeApp{i}' in home for i in range(1, 16))
    assert 'Constants.MAX_HOME_APPS' in (java / 'ui/SettingsFragment.kt').read_text()
    assert 'HomeGridSizing.rowHeight' in home
    assert '(if (fiveRows) 8 else 56).dpToPx()' in home
    print('Fork contract verified: 15 slots, 3 columns, 5 rows, preserved area sizing.')


if __name__ == '__main__':
    check()
