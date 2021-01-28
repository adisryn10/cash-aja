$('.card-body.active').show();

var i;
var acc = document.getElementsByClassName("btn-link-faq");

for (i = 0; i < acc.length; i++) {
  acc[i].addEventListener("click", function () {
    this.classList.toggle("active");
    var panel = this.nextElementSibling;
    if (panel.style.maxHeight) {
      panel.style.maxHeight = null;
    } else {
      panel.style.maxHeight = panel.scrollHeight + "px";
    }
  });
}

$(document).ready(function () {
  var scroll_pos = 0;
  $(document).scroll(function () {
    scroll_pos = $(this).scrollTop();
    if (scroll_pos > 80) {
      $(".faq-content").css('background-color', '#f6f6f6');
      $(".faq-text").css('color', '#2E2727');
      $(".faq-content").css('color', 'gray');
    } else {
      $(".faq-content").css('background-color', '#1B6DB4');
      $(".faq-text").css('color', '#f6f6f6');
    }
  });
});