const showButtons = document.querySelectorAll(".news-toggle");
showButtons.forEach((showButton) => {
  showButton.addEventListener("click", () => {
    const sourceContainer = showButton.closest(".source-container");
    const isExpanded = sourceContainer.classList.toggle("expanded");

    if (isExpanded) {
      showButton.textContent = "Show Less";
    } else {
      showButton.textContent = "Show More";
      sourceContainer.scrollIntoView({
        behavior: "smooth"
      });
    }
  });
});
