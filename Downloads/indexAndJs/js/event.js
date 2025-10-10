//โค้ดเกี่ยวกับ Handle Empty & Error State ตาม task5:US1
async function loadEvents() {
  const eventContainer = document.getElementById("event-list");
  const messageBox = document.getElementById("message-box");

  try {
    const res = await fetch("/api/events");

    // ถ้า backend ส่ง error code (500, 404)
    if (!res.ok) {
      const errorData = await res.json();
      messageBox.innerHTML = `<p style="color:red;">⚠️ ${errorData.error || "เกิดข้อผิดพลาดจากเซิร์ฟเวอร์"}</p>`;
      return;
    }

    const data = await res.json();

    // ถ้า backend ส่ง message เช่น "ไม่มีข้อมูลกิจกรรม"
    if (data.message) {
      messageBox.innerHTML = `<p style="color:gray;">💤 ${data.message}</p>`;
      return;
    }

    // ถ้ามีข้อมูล event
    eventContainer.innerHTML = "";
    data.content.forEach((e) => {
      const div = document.createElement("div");
      div.className = "event-item";
      div.innerHTML = `
        <h3>${e.title}</h3>
        <p>${e.location}</p>
        <p>${e.startDate} - ${e.endDate}</p>
        <hr>
      `;
      eventContainer.appendChild(div);
    });

  } catch (err) {
    // ถ้า server ล่ม หรือ fetch error
    messageBox.innerHTML = `<p style="color:red;">🚫 ไม่สามารถเชื่อมต่อเซิร์ฟเวอร์ได้</p>`;
  }
}

// เรียก function เมื่อหน้าโหลด
document.addEventListener("DOMContentLoaded", loadEvents);