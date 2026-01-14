import { defineStore } from "pinia";
import { ref } from "vue";
import { getProducts, createProduct, updateProduct, deleteProduct } from "@/api/products";

export const useProductStore = defineStore("product", () => {
  // State
  const products = ref([]);
  const totalItems = ref(0);

  const fetchProducts = async (page, size) => {
    const response = await getProducts(page, size);
    products.value = response.data.content;
    totalItems.value = response.data.totalElements;
  };

  const create = async (product) => {
    await createProduct(product);
  };

  const update = async (id, product) => {
    await updateProduct(id, product);
  };

  const remove = async (id) => {
    await deleteProduct(id);
  };

  return {
    products,
    totalItems,
    fetchProducts,
    create,
    update,
    remove,
  };
});
