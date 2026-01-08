
export const getPhoto = (memberPhoto) => {
  if (!memberPhoto) return "";
  if (memberPhoto.startsWith("data:image")) return memberPhoto;
  return `data:image/jpeg;base64,${memberPhoto}`;
};
