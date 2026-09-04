const sourceContainers = document.querySelectorAll(".source-container");
console.log(sourceContainers.length);
sourceContainers.forEach((sourceContainer) => {
  const itemElements = sourceContainer.querySelectorAll(".news-item");
  console.log(itemElements.length);
  itemElements.forEach((item, index) => {
    if (index >= 2) {
      item.classList.add("hidden");
    }
  });
});
