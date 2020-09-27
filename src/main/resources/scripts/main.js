'use strict';

const blackTextColor = 'rgba(0, 0, 0, 0.87)';
const mainThemeColor = 'rgb(255, 54, 175)'
const cells = document.querySelectorAll('td');
const allSelectionBars = document.querySelectorAll('td > .selection_bar');

function highlightThis(e) {
    const rowCells = (e.target.tagName === 'SPAN' || e.target.className === "selection_bar"
        ? e.target.parentElement.parentElement.querySelectorAll('td')
        : e.target.parentElement.querySelectorAll('td'));

    for (const cell of rowCells) {
        cell.style.backgroundColor =
            (cell.style.backgroundColor === mainThemeColor ? 'white' : mainThemeColor);
        cell.style.color =
            (cell.style.color === 'white' ? blackTextColor : 'white');

        // Preserve range item text colour. (contrast ratio issue)
        for (const span of cell.querySelectorAll('.matched_range')) span.style.color = blackTextColor;
    }
}

function enableThisElementsSelection(e) {
    const rowSelectionBars = (e.target.tagName === 'SPAN' || e.target.className === "selection_bar"
        ? e.target.parentElement.parentElement.querySelectorAll('.selection_bar')
        : e.target.parentElement.querySelectorAll('.selection_bar'));

    for (const bar of rowSelectionBars) bar.style.visibility = 'visible';
}

function disableThisElementsSelection(e) {
    const rowSelectionBars = (e.target.tagName === 'SPAN' || e.target.className === "selection_bar"
        ? e.target.parentElement.parentElement.querySelectorAll('.selection_bar')
        : e.target.parentElement.querySelectorAll('.selection_bar'));

    for (const bar of rowSelectionBars) bar.style.visibility = 'hidden';
}

for (const bar of allSelectionBars) bar.style.visibility = 'hidden';
for (const row of cells) row.addEventListener('mouseover', enableThisElementsSelection);
for (const row of cells) row.addEventListener('mouseout', disableThisElementsSelection);
for (const row of cells) row.addEventListener('click', highlightThis);
