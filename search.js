const btn = document.getElementById("event-button");
const menu = document.getElementById("event-menu");

btn.addEventListener("click", () => {
  menu.classList.toggle("show");
  btn.classList.toggle("active");
});

document.addEventListener("click", (e) => {
  if (!btn.contains(e.target) && !menu.contains(e.target)) {
    menu.classList.remove("show");
    btn.classList.remove("active");
  }
});

// JavaScript
function toggleFilterDropdown() {
  const dropdown = document.getElementById("filterDropdownList");
  const button = document.querySelector(".filter-dropdown-button");

  dropdown.classList.toggle("show");
  button.classList.toggle("active");
}

function selectFilter(event, option) {
  event.stopPropagation();

  const textElement = document.querySelector(".filter-dropdown-text");
  textElement.textContent = option;

  const dropdown = document.getElementById("filterDropdownList");
  const button = document.querySelector(".filter-dropdown-button");

  dropdown.classList.remove("show");
  button.classList.remove("active");

  console.log("Selected filter:", option);
}

// ปิด dropdown เมื่อคลิกข้างนอก
document.addEventListener("click", function (event) {
  const wrapper = document.querySelector(".filter-dropdown-wrapper");

  if (wrapper && !wrapper.contains(event.target)) {
    const dropdown = document.getElementById("filterDropdownList");
    const button = document.querySelector(".filter-dropdown-button");

    if (dropdown) dropdown.classList.remove("show");
    if (button) button.classList.remove("active");
  }
});

(function () {
  // ชื่อ function เฉพาะ: EWDate_onSubmit เพื่อไม่ชนกับฟังก์ชันอื่น
  window.EWDate_onSubmit = function () {
    var from = document.getElementById("ew-from")?.value.trim() || "";
    var to = document.getElementById("ew-to")?.value.trim() || "";

    // ตัวอย่าง validation แบบง่าย: ถ้าว่างทั้งคู่ ให้แจ้งผู้ใช้
    if (!from && !to) {
      alert("กรุณาใส่วันที่ตั้งแต่หรือถึงอย่างน้อยหนึ่งค่า");
      return;
    }

    // ตัวอย่าง: ส่งค่าไปฟิลเตอร์ผล (ปรับตามระบบจริงของคุณ)
    // ที่นี่ผมแสดง alert เป็นตัวอย่าง — คุณสามารถแทนที่ด้วยการเรียก API หรือ
    // ตั้งค่า query params แล้ว reload หน้าได้ตามต้องการ
    alert(
      "ค้นหาจาก: " + (from || "(ไม่ระบุ)") + "\nถึง: " + (to || "(ไม่ระบุ)")
    );

    // ตัวอย่าง: ถ้าคุณต้องการตั้งค่าใน input search หลักของหน้า (ถ้ามี)
    // var mainSearch = document.querySelector('.search-input');
    // if(mainSearch){
    //   mainSearch.value = 'from:' + from + ' to:' + to;
    // }
  };

  // optional: เanble Enter key inside the ew-input to trigger submit
  document.addEventListener("keydown", function (e) {
    if (e.key === "Enter") {
      var active = document.activeElement;
      if (active && (active.id === "ew-from" || active.id === "ew-to")) {
        // ป้องกันจากการ submit ฟอร์มอื่น ๆ
        e.preventDefault();
        window.EWDate_onSubmit();
      }
    }
  });
})();

/* ==== EW date picker script (append) ==== */
(function () {
  window.EWDate_onSubmit = function () {
    var from = document.getElementById("ew-from")?.value.trim() || "";
    var to = document.getElementById("ew-to")?.value.trim() || "";

    if (!from && !to) {
      alert("กรุณาใส่วันที่ตั้งแต่หรือถึงอย่างน้อยหนึ่งค่า");
      return;
    }

    // ตัวอย่างการใช้งาน: คุณสามารถเปลี่ยนเป็นส่งค่าไปเรียก API หรือเปลี่ยน query params ตามระบบของคุณ
    alert(
      "ค้นหาจาก: " + (from || "(ไม่ระบุ)") + "\nถึง: " + (to || "(ไม่ระบุ)")
    );
  };

  // enter key triggers submit for the two inputs
  document.addEventListener("keydown", function (e) {
    if (e.key === "Enter") {
      var active = document.activeElement;
      if (active && (active.id === "ew-from" || active.id === "ew-to")) {
        e.preventDefault();
        window.EWDate_onSubmit();
      }
    }
  });
})();
